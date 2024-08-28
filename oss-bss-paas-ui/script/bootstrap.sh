#!/bin/bash

echo "----------------------------------"
echo "Installing packages..."
echo ""

cd client-ui/home

cdt2 package install --autofill

echo "----------------------------------"
echo "Linking child applications..."
echo ""

for childApp in ../*; do
    echo "Found folder ${childApp}"
    if [ "$childApp" == "../home" ]; then
       echo "skipping..."
       continue
    fi

    cdt2 package link $childApp
done
