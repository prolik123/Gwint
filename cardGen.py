import cv2

#Image to be pixelized (tested with .jpg .png .webp)
for i in range(1,10):
    input=cv2.imread('inImgs/'+str(i)+'.jpg')

    height,width=input.shape[:2]
    #How many pixels
    w,h=(64, 64)

    #Some computer vision magic
    temp=cv2.resize(input, (w,h), interpolation=cv2.INTER_LINEAR)
    output=cv2.resize(temp, (150, 200), interpolation=cv2.INTER_NEAREST)

    #Save to pixel.jpg
    cv2.imwrite('outImgs/'+str(i)+'.png',output)

#Display to screen
#cv2.imshow('Input', input)
#cv2.imshow('Output', output)

#Press esc to close
#cv2.waitKey(0)