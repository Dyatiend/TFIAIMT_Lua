count = read()
list = {}
for i=1, count do
    list[i] = read()
    list[i] = list[i] + 0
    -- list[i] = read()
end

print("initial array:")

for i = 1, #list do
    print(list[i])
end

-- list = {6, 5, 4, 3, 2, 1}
n = #list
repeat
    nn = 0
    for i = 2, n do
        if list[i-1] > list[i] then
            tmp = list[i-1]
            list[i-1] = list[i]
            list[i] = tmp
            nn = i
        end
    end
    n = nn
until n==0

print("sorted array:")

for i = 1, #list do
    print(list[i])
end
