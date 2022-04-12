import cv2

#Image to be pixelized (tested with .jpg .png .webp)
input=cv2.imread('image.jpg')

height,width=input.shape[:2]
#How many pixels
w,h=(64, 64)

#Some computer vision magic
temp=cv2.resize(input, (w,h), interpolation=cv2.INTER_LINEAR)
output=cv2.resize(temp, (width, height), interpolation=cv2.INTER_NEAREST)

#Save to pixel.jpg
cv2.imwrite('pixel.jpg',output)

#Display to screen
cv2.imshow('Input', input)
cv2.imshow('Output', output)

#Press esc to close
cv2.waitKey(0)