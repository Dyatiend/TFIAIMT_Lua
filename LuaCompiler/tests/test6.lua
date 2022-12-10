function f(a, b)
    print(a, b)
end

function f1(a, b, ...)
    c = ...
    d = ...
    print(a, b, c, d)
    return nil
end

f(10, 2)

f(1)

f()

print(f(1,2))

print(f1(1,2, 3, 4, 5))
