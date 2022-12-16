function a(b)
    b = b + 5
    print("inside", b)
end

e = 1

print("before", e)
a(e)
print("after", e)

print("----------")

function a(b)
    b = b + 5.6
    print("inside", b)
end

e = 1.6

print("before", e)
a(e)
print("after", e)

print("----------")

function a(b)
    b = "asd".."jaja"
    print("inside", b)
end

e = "asd"

print("before", e)
a(e)
print("after", e)

print("----------")

function a(b)
    b = false
    print("inside", b)
end

e = true

print("before", e)
a(e)
print("after", e)

print("----------")

function a(b)
    b = {2, 4}
    print("inside", b[1], b[2])
end

e = {1, 2}

print("before", e[1], e[2])
a(e)
print("after", e[1], e[2])

print("----------")

function a(b)
    b["asd"] = 4
    print("inside", b[1], b[2], b["asd"])
end

e = {1, 2}

print("before", e[1], e[2], e["asd"])
a(e)
print("after", e[1], e[2], e["asd"])

print("----------")

function a(b)
    function r()
        print(2)
    end
    b = r
    print("inside", b())
end

function e()
    print(1)
end

print("before", e())
a(e)
print("after", e())

print("----------")
