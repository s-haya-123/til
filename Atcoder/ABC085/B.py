# -*- coding: utf-8 -*-
N = int(input())

mochi =[]
for i in range(0,N):
    d = int(input())
    if not d in mochi:
        mochi.append(d)

print(len(mochi))