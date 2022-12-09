function t()
    print(1)
    return true
end

function f()
    print(2)
    return false
end

a = f() and t()

print("------")

b = t() or f()

print(a)
print(b)