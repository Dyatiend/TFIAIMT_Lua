for i = 2, n do
    if list[i-1] > list[i] then
        tmp = list[i-1]
        list[i-1] = list[i]
        list[i] = tmp
        nn = i
    end
end

for i = 0, n, 4 do
    if list[i-1] > list[i] then
        tmp = list[i-1]
        list[i-1] = list[i]
        list[i] = tmp
        nn = i
    end
end

for i = 0, n, 4+4 do
    if list[i-1] > list[i] then
        tmp = list[i-1]
        list[i-1] = list[i]
        list[i] = tmp
        nn = i
    end
end