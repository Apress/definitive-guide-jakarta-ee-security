#!/bin/bash
security import id_rsa.pub -k ~/Library/Keychains/login.keychain
security import id_rsa.key -k ~/Library/Keychains/login.keychain
