--
-- Created by IntelliJ IDEA.
-- User: lee
--

local key = "rate.limit:" .. KEYS[1]
local limit = tonumber(ARGV[1])
local expire_time = ARGV[2]

local is_exists = redis.call("EXISTS", key)
if is_exists == 1 then
    local num = redis.call("GET", key)
    local totalLimit = tonumber(num)
    if totalLimit > limit then
        return 0
    else
        redis.call("INCR", key)
        return 1
    end
else
    redis.call("SET", key, 1)
    redis.call("EXPIRE", key, expire_time)
    return 1
end

