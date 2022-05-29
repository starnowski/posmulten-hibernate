#!/bin/bash

DIRNAME="$(dirname $0)"

# Setting gpg directory path
export GPG_DIR="$(dirname $0)"

# Decrypting key files
openssl aes-256-cbc -d -pass pass:$ENCRYPTION_PASSWORD -pbkdf2 -in $GPG_DIR/secring.gpg.enc -out $GPG_DIR/secring.gpg
openssl aes-256-cbc -d -pass pass:$ENCRYPTION_PASSWORD -pbkdf2 -in $GPG_DIR/public.gpg.enc -out $GPG_DIR/pubring.gpg
#"${DIRNAME}/../mvnw" deploy --settings $GPG_DIR/settings.xml -DperformRelease=true -DskipTests=true -P maven-central-deploy
#Test
gpg --import $GPG_DIR/secring.gpg
gpg --import $GPG_DIR/pubring.gpg
"${DIRNAME}/../mvnw" clean install -DperformRelease=true -DskipTests=true
exit $?