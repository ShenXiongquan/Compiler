#!/bin/bash

echo "test begin!"
echo "===== terminal output ====="
llvm-link llvm_ir.txt lib.ll -S -o out.ll
lli out.ll
printf "\n"
echo "==========================="
echo "test end!