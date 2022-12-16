local function add(a,b)
   print(a, b)
    assert(a < 5, "a is not a number")
    assert(b > 10, "b is not a number")
    print("asdasd")
    return a+b
 end

--  add(10)

function myfunction ()
    n = n/nil
 end
 
 if pcall(add, 1, 2) then
    print("Success")
 else
     print("Failure")
 end
 
 function myerrorhandler( err )
    print( "ERROR:", err )
 end
 
 status = xpcall( myfunction, myerrorhandler, 10, 20 )
 print( status)
 