import os
from datetime import datetime
from PIL import Image

def getRatio(cropped, pattern):                    
    croppedPixels = cropped.convert("RGB").load()
    patternPixels = pattern.convert("RGB").load()
    #display(cropped)
    #display(pattern)    
    
    blackCounter=0
    similarityCounter=0
    
    if cropped.size != pattern.size:
        print("Sizes do not match: ", cropped.size, ", ", pattern.size)
        return -1
    
    width, length = cropped.size    
    for w in range(width):
        for l in range(length):
            if croppedPixels[w,l]==(0,0,0) or patternPixels[w,l]==(0,0,0):
                blackCounter = blackCounter+1
                
                if croppedPixels[w,l]==(0,0,0) and patternPixels[w,l]==(0,0,0):
                    similarityCounter=similarityCounter+1

    if blackCounter==0:
        return -1
        
    return similarityCounter/blackCounter

#-----------------------------------------------------------------------------
patternsFolder = "D:\\BA\\Codes\\zTools\\patterns_digits_cpu\\"
patternNames = sorted(
    [os.path.splitext(f)[0] for f in os.listdir(patternsFolder) if os.path.isfile(os.path.join(patternsFolder, f))],
    key=lambda x: int(x)  
)

patterns = []
for patternName in patternNames:
    patterns.append(Image.open(patternsFolder + patternName + ".png"))
    
#-----------------------------------------------------------------------------    
def findPatternForCropped(cropped):
    cropped = cropped.convert("L").point(lambda x: 255 if x > 128 else 0, mode="1")    
    maxRatio = 0
    result = -1

    for pattern in patterns: 
        ratio = getRatio(cropped, pattern)

        if ratio == -1:        
            return 0, 1            
        
        if ratio > maxRatio:
            maxRatio = ratio
            result = int(patternNames[patterns.index(pattern)][0])

    return result, maxRatio
