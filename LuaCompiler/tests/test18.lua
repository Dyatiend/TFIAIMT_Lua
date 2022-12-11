-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- return 1
-- end

-- mt.__add = add

-- setmetatable(a, mt)
-- -- setmetatable(b, mt)

-- print(a + b)
-- print(b + a)

--------------------------------------------

-- a = {1}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- end

-- -- mt.__call = add

-- setmetatable(a, mt)

-- print(a(1, 2))

--------------------------------------------

-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- end

-- mt.__len = add

-- setmetatable(a, mt)
-- setmetatable(b, mt)

-- print(#a)

--------------------------------------------

-- a = {}
-- b = {}
-- mt = {}


-- function add(a1, ...)
-- print(a1, ...)
-- end

-- mt.__concat = add


-- setmetatable(a, mt)
-- -- setmetatable(b, mt)

-- print(a .. b)

--------------------------------------------

-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- end

-- mt.__eq = add

-- -- setmetatable(a, mt)
-- --setmetatable(b, mt)

-- print(b == a)

--------------------------------------------

-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- return false
-- end

-- mt.__lt = add

-- setmetatable(a, mt)
-- --setmetatable(b, mt)

-- print(b < a)

--------------------------------------------

-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- return nil, nil
-- end

-- function d()
--     return 1, 2
-- end

-- mt.__le = add

-- setmetatable(a, mt)
-- --setmetatable(b, mt)

-- print( d() <= a)

--------------------------------------------

-- a = {}
-- b= {}
-- mt = {}

-- function add(a, b)
-- print(a, b)
-- return nil, nil
-- end

-- function d()
--     return 1, 2
-- end

-- mt.__index = add

-- setmetatable(a, mt)

-- print( a[d()])

--------------------------------------------

-- function d()
--     return 3, 2
-- end
-- a = {}
-- b= {}
-- c = {d(), 2}
-- mt = {}
-- mt1 = {}

-- function add(a, b)
-- print(a, b)
-- return nil, nil
-- end

-- mt.__index = b
-- mt1.__index = c

-- setmetatable(a, mt)
-- setmetatable(b, mt1)

-- print( a[1])

--------------------------------------------

-- a = {}
-- b= {}
-- c = {1, 2}
-- mt = {}
-- mt1 = {}

-- function add(a, b, c)
-- print(a, b, c)
-- return nil, nil
-- end

-- function d()
--     return 1, 2
-- end

-- mt.__newindex = add
-- mt1.__index = c

-- setmetatable(a, mt)
-- setmetatable(b, mt1)

-- a[1] = 2
-- print(a[1] )

--------------------------------------------

a = {}
b= {}
c = {1,2}
mt = {}
mt1 = {}

function d()
    return 1, 2
end

mt.__newindex = b
mt1.__newindex = c

setmetatable(a, mt)
setmetatable(b, mt1)

a[d()] = 3
print(a[d()] )
print(b[d()] )
print(c[d()] )

--------------------------------------------

-- a = {}
-- b = {}
-- mt = {}


-- setmetatable(a, nil)
-- -- setmetatable(b, mt)

-- print(a + b)