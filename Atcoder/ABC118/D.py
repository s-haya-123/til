# -*- coding: utf-8 -*-

N,M = map(int, input().split())

As = list(map(int, input().split()))

mattis = [2,5,5,4,5,6,3,7,6]

max = [[]]*M


As_mattis = list(map(lambda x: mattis[x-1], As))
print(As_mattis)
for i in range(0,M):
    A = As[i]
    matti = As_mattis[i]
    num = int(N/matti)
    while(True):
        rem = N - num * matti
        if num < 0:
            break
        elif rem ==0 or rem in As_mattis:
            max[i] = [A] * num 
            if rem in As_mattis:
                max[i].append(sorted([As[i] for i in range(0,len(As_mattis)) if As_mattis[i] == rem],reverse=True)[0])
            
        else:
            num -= 1
            
max_str = ""
for max_list in max:
    if len(max_list) > len(max_str):
        print(max_list)
        max_str = "".join(map(lambda x: str(x),sorted(max_list,reverse=True)))
    
print(max_str)
