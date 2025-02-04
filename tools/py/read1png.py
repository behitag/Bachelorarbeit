import os
from datetime import datetime
from PIL import Image
import SimilarityForName
import SimilarityForDigit
import SimilarityForCPU

z0_x1 = 938
z0_x2 = 956
z1_x1 = 956
z1_x2 = 974
z2_x1 = 974
z2_x2 = 992
z0_y1 = 185
z0_y2 = 220
z1_y1 = z0_y1
z1_y2 = z0_y2
z2_y1 = z0_y1
z2_y2 = z0_y2
a_x1 = 100
a_x2 = 880
b_x1 = 956
b_x2 = 968
c_x1 = 968
c_x2 = 980
d_x1 = 986
d_x2 = 998
y0 = 266
height = 56
#------------------------------------------------------------------ 
class Dataset:
    def __init__(self, time, cpuTotal, names, cpus, minRatio):
        self.time = time
        self.cpuTotal = cpuTotal
        self.names = list(names)
        self.cpus = list(cpus)
        self.minRatio = minRatio
#------------------------------------------------------------------ 
def read(filePath):
    names = []
    cpus = []
    ss = Image.open(filePath)
    minRatio = 1
    
    area = (z0_x1, z0_y1, z0_x2, z0_y2)
    cropped = ss.crop(area)
    #display(cropped)
    cpuTotal0 = SimilarityForCPU.findPatternForCropped(cropped)[0]
    ratio = SimilarityForCPU.findPatternForCropped(cropped)[1]
    if ratio < minRatio:
        minRatio = ratio
    
    area = (z1_x1, z1_y1, z1_x2, z1_y2)
    cropped = ss.crop(area)
    cpuTotal1 = SimilarityForCPU.findPatternForCropped(cropped)[0]
    ratio = SimilarityForCPU.findPatternForCropped(cropped)[1]
    if ratio < minRatio:
        minRatio = ratio
    
    area = (z2_x1, z2_y1, z2_x2, z2_y2)
    cropped = ss.crop(area)
    cpuTotal2 = SimilarityForCPU.findPatternForCropped(cropped)[0]
    ratio = SimilarityForCPU.findPatternForCropped(cropped)[1]
    if ratio < minRatio:
        minRatio = ratio
    
    cpuTotal = int(str(cpuTotal0) + str(cpuTotal1) + str(cpuTotal2))
    
    for i in range(13):        
        area = (a_x1, y0+i*height, a_x2, y0+(i+1)*height)
        cropped = ss.crop(area)
        name = SimilarityForName.findPatternForCropped(cropped)        
        names.append(name)
        
        area = (b_x1, y0+i*height, b_x2, y0+(i+1)*height)
        cropped = ss.crop(area)
        cpu1 = SimilarityForDigit.findPatternForCropped(cropped)[0]
        ratio = SimilarityForDigit.findPatternForCropped(cropped)[1]
        if ratio < minRatio:
            minRatio = ratio
        
        area = (c_x1, y0+i*height, c_x2, y0+(i+1)*height)
        cropped = ss.crop(area)
        cpu2 = SimilarityForDigit.findPatternForCropped(cropped)[0]
        ratio = SimilarityForDigit.findPatternForCropped(cropped)[1]
        if ratio < minRatio:
            minRatio = ratio
        
        area = (d_x1, y0+i*height, d_x2, y0+(i+1)*height)
        cropped = ss.crop(area)
        cpu3 = SimilarityForDigit.findPatternForCropped(cropped)[0]
        ratio = SimilarityForDigit.findPatternForCropped(cropped)[1]
        if ratio < minRatio:
            minRatio = ratio
        
        cpu = int(str(cpu1) + str(cpu2) + str(cpu3))
        cpus.append(cpu)
    
        time = datetime.fromtimestamp(os.path.getctime(filePath)).strftime('%Y-%m-%d %H:%M:%S')
    
        result = Dataset(time, cpuTotal, names, cpus, minRatio)    
    
    return result