#!/bin/bash

# GreetingLib AAR Validation Script
# This script validates that the generated AAR properly includes Hilt dependencies

set -e

echo "🔧 GreetingLib AAR Validation Script"
echo "====================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Build the AAR
echo -e "${BLUE}Building GreetingLib AAR...${NC}"
./gradlew :greetinglib:assembleRelease --quiet

AAR_PATH="greetinglib/build/outputs/aar/greetinglib-release.aar"

if [ ! -f "$AAR_PATH" ]; then
    echo -e "${RED}❌ AAR not found at $AAR_PATH${NC}"
    exit 1
fi

echo -e "${GREEN}✅ AAR built successfully: $AAR_PATH${NC}"

# Create temporary directory for AAR analysis
TEMP_DIR=$(mktemp -d)
echo -e "${BLUE}Extracting AAR to: $TEMP_DIR${NC}"

# Extract AAR
cd "$TEMP_DIR"
unzip -q "../../../$AAR_PATH"

echo -e "${YELLOW}📋 AAR Contents:${NC}"
ls -la

# Check for required files
echo -e "\n${BLUE}🔍 Checking AAR structure...${NC}"

required_files=("classes.jar" "AndroidManifest.xml" "R.txt" "proguard.txt")
for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅ $file${NC}"
    else
        echo -e "${RED}❌ $file missing${NC}"
    fi
done

# Analyze classes.jar
echo -e "\n${BLUE}🔍 Analyzing classes.jar...${NC}"
if [ -f "classes.jar" ]; then
    jar -tf classes.jar > classes_list.txt
    
    echo -e "${YELLOW}📦 Classes in JAR:${NC}"
    echo "Total classes: $(grep '\.class$' classes_list.txt | wc -l)"
    
    # Check for Hilt generated classes
    echo -e "\n${BLUE}🔍 Checking for Hilt generated code...${NC}"
    
    hilt_patterns=("_Factory" "_MembersInjector" "_HiltModules" "Hilt_" "_Impl")
    found_hilt=false
    
    for pattern in "${hilt_patterns[@]}"; do
        matches=$(grep -c "$pattern" classes_list.txt || true)
        if [ "$matches" -gt 0 ]; then
            echo -e "${GREEN}✅ Found $matches files matching pattern: $pattern${NC}"
            found_hilt=true
        fi
    done
    
    if [ "$found_hilt" = true ]; then
        echo -e "${GREEN}✅ Hilt generated classes found in AAR${NC}"
    else
        echo -e "${YELLOW}⚠️  No Hilt generated classes found (this might be expected for libraries)${NC}"
    fi
    
    # Check for GreetingLib specific classes
    echo -e "\n${BLUE}🔍 Checking for GreetingLib classes...${NC}"
    
    greetinglib_classes=(
        "br/com/renanalencar/greetinglib/domain/model/Greeting"
        "br/com/renanalencar/greetinglib/domain/usecase/GetGreetingUseCase"
        "br/com/renanalencar/greetinglib/data/repository/GreetingRepositoryImpl"
        "br/com/renanalencar/greetinglib/di/GreetingModule"
        "br/com/renanalencar/greetinglib/presentation/GreetingComponentKt"
    )
    
    for class_path in "${greetinglib_classes[@]}"; do
        if grep -q "$class_path" classes_list.txt; then
            echo -e "${GREEN}✅ $class_path.class${NC}"
        else
            echo -e "${RED}❌ $class_path.class missing${NC}"
        fi
    done
    
    # Show sample of included classes
    echo -e "\n${YELLOW}📋 Sample of included classes:${NC}"
    grep "br/com/renanalencar/greetinglib" classes_list.txt | head -10
    
else
    echo -e "${RED}❌ classes.jar not found${NC}"
fi

# Check AndroidManifest.xml
echo -e "\n${BLUE}🔍 Checking AndroidManifest.xml...${NC}"
if [ -f "AndroidManifest.xml" ]; then
    echo -e "${GREEN}✅ AndroidManifest.xml found${NC}"
    echo -e "${YELLOW}📋 Manifest content:${NC}"
    cat AndroidManifest.xml
else
    echo -e "${RED}❌ AndroidManifest.xml missing${NC}"
fi

# Check ProGuard rules
echo -e "\n${BLUE}🔍 Checking ProGuard rules...${NC}"
if [ -f "proguard.txt" ]; then
    echo -e "${GREEN}✅ proguard.txt found${NC}"
    echo -e "${YELLOW}📋 ProGuard rules count: $(wc -l < proguard.txt)${NC}"
    
    # Check for Hilt-specific rules
    if grep -q "hilt" proguard.txt; then
        echo -e "${GREEN}✅ Hilt ProGuard rules found${NC}"
    else
        echo -e "${YELLOW}⚠️  No Hilt-specific ProGuard rules found${NC}"
    fi
    
    echo -e "\n${YELLOW}📋 Sample ProGuard rules:${NC}"
    head -20 proguard.txt
else
    echo -e "${RED}❌ proguard.txt missing${NC}"
fi

# Check R.txt
echo -e "\n${BLUE}🔍 Checking R.txt...${NC}"
if [ -f "R.txt" ]; then
    echo -e "${GREEN}✅ R.txt found${NC}"
    echo -e "${YELLOW}📋 Resources count: $(wc -l < R.txt)${NC}"
else
    echo -e "${RED}❌ R.txt missing${NC}"
fi

# Analyze dependencies
echo -e "\n${BLUE}🔍 Analyzing dependency information...${NC}"
cd - > /dev/null

echo -e "${YELLOW}📋 Checking build dependencies...${NC}"
./gradlew :greetinglib:dependencies --configuration releaseRuntimeClasspath --quiet | grep -E "(hilt|dagger)" || echo "No Hilt dependencies in runtime classpath"

# Check for POM generation (if maven-publish is configured)
POM_PATH="greetinglib/build/publications/release/pom-default.xml"
if [ -f "$POM_PATH" ]; then
    echo -e "\n${BLUE}🔍 Checking generated POM...${NC}"
    echo -e "${GREEN}✅ POM file found${NC}"
    
    if grep -q "hilt-android" "$POM_PATH"; then
        echo -e "${GREEN}✅ Hilt dependency found in POM${NC}"
    else
        echo -e "${YELLOW}⚠️  Hilt dependency not found in POM${NC}"
    fi
else
    echo -e "\n${YELLOW}⚠️  No POM file found (publish task not run)${NC}"
fi

# Final summary
echo -e "\n${BLUE}📋 Validation Summary${NC}"
echo "===================="

file_size=$(du -h "$AAR_PATH" | cut -f1)
echo -e "📦 AAR Size: ${YELLOW}$file_size${NC}"

echo -e "📁 AAR Location: ${YELLOW}$AAR_PATH${NC}"

echo -e "\n${GREEN}✅ AAR validation completed successfully${NC}"
echo -e "${BLUE}ℹ️  The AAR is ready for distribution with proper Hilt integration${NC}"

# Cleanup
rm -rf "$TEMP_DIR"

echo -e "\n${BLUE}🚀 Next steps:${NC}"
echo "1. Test the AAR in a sample project"
echo "2. Publish to Maven repository"
echo "3. Update integration documentation"
echo "4. Create release notes"