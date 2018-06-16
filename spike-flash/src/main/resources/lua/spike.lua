--

local totalLen = redis.call('llen', KEYS[1]) --查询队列的长度

if totalLen >= 5 then
    return 0
end

local currentLen = redis.call('lpush', KEYS[1], ARGV[1])

if currentLen >= 5 then
    return 0
end

return 1



