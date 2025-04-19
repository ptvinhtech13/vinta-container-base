#!/bin/bash

# Directories to scan (space-separated)
#DIRS_TO_SCAN="app/src web/src"
DIRS_TO_SCAN="app common core data-access web"

# Create a temporary file for sed pattern
cat > /tmp/header_pattern << 'EOF'
# Remove any comment block at the start of the file
1{
  /^\/\*/{
    :a
    /\*\//!{
      N
      ba
    }
    /\*\//d
  }
}
EOF

# Function to process files
process_file() {
    local file="$1"
    echo "Processing: $file"
    # Create backup
    cp "$file" "${file}.bak"
    # Remove headers
    sed -i -f /tmp/header_pattern "$file"
    # Remove empty lines at start of file
    sed -i '/./,$!d' "$file"
    # Remove multiple blank lines with single blank line
    sed -i '/^$/N;/^\n$/D' "$file"
    rm -f "${file}.bak"
}

# Main loop
for dir in $DIRS_TO_SCAN; do
    if [ -d "$dir" ]; then
        echo "Scanning directory: $dir"
        # Find all potential source files
        find "$dir" -type f \( -name "*.java" -o -name "*.js" -o -name "*.cpp" -o -name "*.h" \) | while read -r file; do
            process_file "$file"
        done
    else
        echo "Directory not found: $dir"
    fi
done

# Cleanup
rm -f /tmp/header_pattern

echo "Done!"