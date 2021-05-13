class MemberRepository {
    fun join(loginId: String, loginPw: String, name: String, nickname: String, cellphoneNo: String, email: String) {

        val id = ++memberLastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        members.add(Member(id, loginId, loginPw, regDate, updateDate, name, nickname, cellphoneNo, email))

    }

    fun isUsableLoginId(loginId: String): Boolean {
        for(member in members){
            if(member.loginId == loginId){
                return false
            }
        }
        return false
    }

    fun getMemberByLoginId(loginId: String): Member? {
        for(member in members){
            if(member.loginId == loginId){
                return member
            }
        }
        return null

    }

    fun getMemberById(memberId: Int): Member? {
        for(member in members){
            if(member.id == memberId){
                return member
            }
        }
        return null

    }


    var memberLastId = 0

    var members = mutableListOf<Member>()
}