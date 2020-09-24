numbers = [4,6,2,10,100]
output = []
numbers = sorted(numbers)
nowmin = 10 ** 9
    
for ind in range(1, len(numbers)):
    diff = abs(numbers[ind-1] - numbers[ind])
        
    if diff < nowmin:
        output = [(numbers[ind-1], numbers[ind])]
        nowmin = diff
    elif diff == nowmin:
        output.append((numbers[ind-1], numbers[ind]))
            
flat_list = [item for sublist in output for item in sublist]
for i in range(0,len(flat_list),2):
    print(flat_list[i], flat_list[i+1])