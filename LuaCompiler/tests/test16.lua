function a(a, ...)
    print(a, ...)
end

if #read() > 3 then
    local function a(...)
        print(...)
    end

    a(4, 5, 7, 10)
else
    local function a(a, b, c)
        print(a, b, c)
    end

    a(1, 2, 3)
end

a(6, 15, 20, 25, 777, 7885, 537, 6.25, "Hi", false)