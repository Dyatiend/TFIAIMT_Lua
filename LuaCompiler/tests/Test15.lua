function a() end

a()

if true then
    local function a() end
    a()
    b()
end