print(true or true)
print(false or true)
print(true or false)
print(false or false)

print(true and true)
print(false and true)
print(true and false)
print(false and false)

print("-------------------------")
function d()
    return 3, 4
end

function b()
    return false, 4
end

function b1()
    return 1, 4
end

print(nil or 1)
print(1 or 2)
print(nil or d())
print(nil or nil)

print(2.2 or "asd")

a = {}
print(a or d())
print(a or false)
print(a or true)
print(a or nil)
print(a or 2.2)
print(a or 2)
print(a or "asd")

print(false or d())

print(b() and d())
print(b1() or d())
