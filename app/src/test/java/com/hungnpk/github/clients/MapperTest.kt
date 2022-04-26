package com.hungnpk.github.clients

import com.hungnpk.github.clients.data.mapper.toUser
import com.hungnpk.github.clients.data.mapper.toUsers
import com.hungnpk.github.clients.domain.response.UserDetailResponse
import com.hungnpk.github.clients.domain.response.UserListResponse
import org.junit.Assert
import org.junit.Test

class MapperTest {
    @Test
    fun mapUserListResponseToUsers() {
        val usersResponse = UserListResponse(
            items = listOf(
                UserDetailResponse(
                    id = 1,
                    login = "hung",
                    avatarUrl = "https://imgur.com/gallery/nSjv6U7"
                )
            )
        )
        val users = usersResponse.toUsers()
        Assert.assertEquals(
            users[0].id, usersResponse.items!![0].id
        )
        Assert.assertEquals(
            users[0].username, usersResponse.items!![0].login
        )
        Assert.assertEquals(
            users[0].avatarUrl, usersResponse.items!![0].avatarUrl
        )
    }
    @Test
    fun mapUserDetailResponseToUser() {
        val userResponse = UserDetailResponse(
                    id = 1,
                    login = "hung",
                    avatarUrl = "https://imgur.com/gallery/nSjv6U7",
            name = "Hung Nguyen",
            location = "VN",
            company = "Vingroup",
            following = 20,
            followers = 10,
            bio = "Android developer"
        )
        val user = userResponse.toUser()
        Assert.assertEquals(user.name, userResponse.name)
        Assert.assertEquals(user.location, userResponse.location)
        Assert.assertEquals(user.company, userResponse.company)
        Assert.assertEquals(user.followers, userResponse.followers)
        Assert.assertEquals(user.following, userResponse.following)
        Assert.assertEquals(user.biography, userResponse.bio)
    }
}