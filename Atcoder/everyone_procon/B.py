# -*- coding: utf-8 -*-
import sys
sum = [0]*4

for i in range(0,3):
    a, b = map(int, input().split())
    sum[a-1] +=1
    sum[b-1] +=1

for i in range(0,4):
    if sum[i] >= 3:
        print("NO")
        sys.exit()

print("YES")