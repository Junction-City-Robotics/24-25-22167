import sys

import re

while True:
    i = input()
    
    if i.lower() == "x":
        sys.exit()
    
    try:
        if "//" in i:
            continue
            
        if "(" not in i:
            continue
        
        numbers = [float(n) for n in re.findall(r'\d+', i)]
        
        numbers.pop(0)
        numbers.pop(0)
        
        numbers[2] -= 90
        temp = numbers[1]
        numbers[1] = numbers[0]
        numbers[0] = temp
        numbers[1] *= -1
        
        print(f"\t\t\t new Pose2d({numbers[0]}, {numbers[1]}, Math.toRadians({numbers[2]}));\n\n")
    except Exception as e:
        print("Failed:", e)
        