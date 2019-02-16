# -*- coding: utf-8 -*-
from functools import reduce

def gcd(a, b):
    if a < b:
        a, b = b, a
    if b == 0:
        return a
    c = a % b
    return gcd(b, c)
def gcd_list(numbers):
    return reduce(gcd, numbers)

N = int(input())

As = map(int, input().split())

print(gcd_list(As))
