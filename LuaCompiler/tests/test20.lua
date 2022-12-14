function f(n)
    print(n)
    return n
end

a = 1
b = 2
    
r = ( a < b ) and f(a) or f(b)
print(r)