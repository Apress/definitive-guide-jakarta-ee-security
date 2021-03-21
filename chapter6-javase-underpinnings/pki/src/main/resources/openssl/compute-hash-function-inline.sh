#!/bin/bash
# Computes the hash value of an inline string using SHA-256
# Disadvantage: silent echo adds a newline at the end of this string.
# Solution: use the -n option with echo, as shown in compute-hash-function-inline-clean.sh
echo "This is some dummy text" | openssl dgst -sha256