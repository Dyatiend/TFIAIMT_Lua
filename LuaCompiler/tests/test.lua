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


local function add(a,b)
    print(a, b)
     assert(true, "a is not a number")
     assert(false, "b is not a number")
     return a+b
  end
  
 
 function myfunction ()
     n = n/nil
  end
  
  if pcall(add, 10, 20) then
     print("Success")
  else
      print("Failure")
  end
  
  function myerrorhandler( err )
     print( "ERROR:", err )
  end
  
  status = xpcall( myfunction, myerrorhandler, 10, 20 )
  print( status)

  Colors = {
    "BLUE",
    "GREEN",
    "RED",
    "VIOLET",
    "YELLOW",
 }
 

 local color = Colors.RED
 print(color)

 if true then
    a = 3
    local a = 1
    if true then
        a = 2
        print(a)
    end
    print(a)
end

print(a)


count = read()
list = {}
for i=1, count do
    append(list, read())
end
n = #list
repeat
    nn = 0
    for i = 2, n do
        if list[i-1] > list[i] then
            tmp = list[i-1]
            list[i-1] = list[i]
            list[i] = tmp
            nn = i
        end
    end
    n = nn
until n==0

for i = 1, #list do
    print(list[i])
end

d = read()

if d == "asd" then 
	function f()
		print(3)
	end
else
	function f()
		print(2)
	end
end

f()

rows = read()
cols = read()
table = {}
for i=1, rows do
  table[i] = {}
  for j=1, cols do
    table[i][j] = read()
  end
end

i = 1
repeat
  endFlag = true
  maxCol = 1
  minRow = i
  minRowNumb = table[i][1]
  for j=2, cols do
    if minRowNumb > table[i][j] then
      minRowNumb = table[i][j]
      maxCol = j
    end
  end
  maxColNumb = table[i][maxCol]
  for newI=1, rows do
    if maxColNumb < table[newI][maxCol] then 
      endFlag = false
    end
  end
  i = i + 1
until endFlag

print("--------------")
print(table[minRow][maxCol])