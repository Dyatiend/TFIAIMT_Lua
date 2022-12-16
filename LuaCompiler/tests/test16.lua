n = read() + 0

function t()
    print(1)
    return true
end

function f()
    print(2)
    return false
end

a = n < 10 and t()

print("------")

b = n < 10 or f()

print(a)
print(b)