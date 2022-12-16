function f(table, row, col)
    local top
    local bottom
    if table[row-1] ~= nil then
        top = table[row-1][col]
    end
    if table[row+1] ~= nil then
        bottom = table[row+1][col]
    end
    local left = table[row][col-1]
    local right = table[row][col+1]

    if table[row][col] ~= 0 then
        return
    end

    table[row][col] = 2

    if top ~= nil and top ~= 1 and top ~= 2 then
        f(table, row-1, col)
    end
    
    if bottom ~= nil and bottom ~= 1 and bottom ~= 2 then
        f(table, row+1, col)
    end

    if left ~= nil and left ~= 1 and left ~= 2 then
        f(table, row, col-1)
    end

    if right ~= nil and right ~= 1 and right ~= 2 then
        f(table, row, col+1)
    end
end

tbl = {
    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
    {0, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
    {1, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
    {1, 0, 1, 0, 0, 1, 0, 0, 1, 0 },
    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
    {1, 0, 1, 0, 0, 0, 1, 0, 1, 0 },
    {1, 0, 0, 1, 1, 1, 0, 0, 1, 0 },
    {0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
    {0, 0, 1, 1, 1, 1, 1, 0, 0, 0 },
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
}

for i=1, #tbl do
    print(tbl[i][1], tbl[i][2], tbl[i][3], tbl[i][4], tbl[i][5], tbl[i][6], tbl[i][7], tbl[i][8], tbl[i][9], tbl[i][10])
end

a = read() + 0

b = read() + 0

f(tbl, a, b)

print("-----------------------")

for i=1, #tbl do
    print(tbl[i][1], tbl[i][2], tbl[i][3], tbl[i][4], tbl[i][5], tbl[i][6], tbl[i][7], tbl[i][8], tbl[i][9], tbl[i][10])
end