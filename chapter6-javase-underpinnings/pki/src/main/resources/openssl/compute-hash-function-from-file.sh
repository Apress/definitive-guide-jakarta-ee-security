#!/bin/bash
# Creates a file with some dummy text and computes the hash value of its content using SHA-256
echo "This is some dummy text" > dummy.txt
openssl dgst -sha256 dummy.txt
