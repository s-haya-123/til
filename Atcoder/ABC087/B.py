# -*- coding: utf-8 -*-
A_500 = int(input()) #10
B_100 = int(input()) #2
C_50 = int(input()) #1
X = int(input())


cnt = 0
for a in range(0,A_500+1):
    for b in range(0,B_100+1):
        for c in range(0,C_50+1):
            if a * 500 + b * 100 + c * 50 == X:
                cnt += 1

print(cnt)