#!/bin/bash
# Computes the clean hash value of an inline string using SHA-256
echo -n "This is some dummy text" | openssl dgst -sha256