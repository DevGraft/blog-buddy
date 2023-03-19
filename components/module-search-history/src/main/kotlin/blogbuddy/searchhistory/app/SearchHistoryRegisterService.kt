package blogbuddy.searchhistory.app

import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SearchHistoryRegisterService {

    @Transactional
    fun register(request:SearchHistoryRegisterDataRequest) {

    }
}