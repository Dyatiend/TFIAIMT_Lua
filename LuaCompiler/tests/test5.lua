function enum(tbl)
    local length = #tbl
    for i = 1, length do
        local v = tbl[i]
        tbl[v] = i
    end

    return tbl
end

Colors = enum {
   "BLUE",
   "GREEN",
   "RED",
   "VIOLET",
   "YELLOW",
}

-- finally, get our integer from the enum!
local color = Colors.RED
print(color)
