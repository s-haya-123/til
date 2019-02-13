# -*- coding: utf-8 -*-
# 整数の入力
N, K = map(int, input().split())

if int((N+1) /2) >= K:
    print("YES")
else:
    print("NO")