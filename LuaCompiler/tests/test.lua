a = 5
b = 6

print("before function: ", a, b)
function f()
  local a = 1
  a = a + 1
  b = b + 1
  print("inside function: ", a, b)
end

f()

print("after function: ", a, b)

