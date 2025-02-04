import os
from datetime import datetime
from PIL import Image

patternsFolder = "D:\\BA\\Codes\\zTools\\patterns_names\\"
#-----------------------------------------------------------------------------------

def checkEquality(cropped, pattern):    
    #display(cropped)
    #display(pattern)
    #print("---------------")
    croppedPixels = cropped.convert("RGB").load()
    patternPixels = pattern.convert("RGB").load()
    
    if cropped.size != pattern.size:
        print("Sizes do not match: ", cropped.size, ", ", pattern.size)
        return False
    
    width, length = cropped.size    
    for w in range(width):
        for l in range(length):
            if croppedPixels[w,l]!=patternPixels[w,l]:
                return False

    return True
#-----------------------------------------------------------------------------
def findPatternForCropped(cropped):
    cropped = cropped.convert("L").point(lambda x: 255 if x > 128 else 0, mode="1")    
    patternNames = [f for f in os.listdir(patternsFolder) if os.path.isfile(os.path.join(patternsFolder, f))] 
    patterns = []
    for patternName in patternNames:
        patterns.append(Image.open(patternsFolder + patternName))

    for pattern in patterns: 
        result = checkEquality(cropped, pattern)
        
        if result == True:          
            result = patternNames[patterns.index(pattern)][0:-4]            
            return result
     
    files = [f for f in os.listdir(patternsFolder) if os.path.isfile(os.path.join(patternsFolder, f))]
    cropped.save(patternsFolder + str(len(files)+1) + ".png")
    print("Saved new Name into Namesfolder")
    return str(len(files)+1)