# -*- coding: utf-8 -*-
import sys
S = input()

while(len(S) > 0):
    if S[0:6] == "eraser":
        S = S[6:]
    elif S[0:5] == "erase":
        S = S[5:]
    elif S[0:5] == "dream":
        tmp = S[5:8]
        if len(tmp) == 0:
            S = S[5:]
        elif tmp == "era":
            S = S[5:]
        elif tmp == "er":
            S = S[7:]
        elif tmp == "ere":
            S = S[7:]
        elif tmp == "erd":
            S = S[7:]
        else:
            S = S[5:]
    else:
        print("NO")
        sys.exit()
print("YES")