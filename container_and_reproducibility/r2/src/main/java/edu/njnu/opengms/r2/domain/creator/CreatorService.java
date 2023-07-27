package edu.njnu.opengms.r2.domain.creator;


import edu.njnu.opengms.common.enums.ResultEnum;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.JwtUtils;
import edu.njnu.opengms.r2.domain.creator.vo.CreatorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CreatorService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/7
 * @Version 1.0.0
 */
@Service
public class CreatorService {
    @Autowired
    CreatorRepository creatorRepository;

    public CreatorVO findVOByName(String s) {
        return creatorRepository.findVOByName(s).orElse(null);
    }

    public Creator findByName(String s) {
        return creatorRepository.findByName(s).orElse(null);
    }


    public Creator register(String name,String password) {
        if(creatorRepository.findByName(name).isPresent()){
            throw new MyException(ResultEnum.EXIST_OBJECT);
        }
        Creator creator=Creator.builder().name(name).password(password).build();

        return creatorRepository.insert(creator);
    }

    public String login(String name,String password) {
        Creator creatorFromDB = creatorRepository.findByName(name).orElseThrow(MyException::noObject);
        if(creatorFromDB.getPassword().equals(password)){
            String jwtToken = JwtUtils.generateToken(creatorFromDB.getId(), name, password);
            return "Bearer" + " " + jwtToken;
        }else{
            throw new MyException(ResultEnum.USER_PASSWORD_NOT_MATCH);
        }
    }



}
