package hello.controller;

import com.alibaba.fastjson.JSONObject;
import hello.common.ResponseVO;
import hello.common.ResponseStatusEnum;
import hello.config.exception.MyRuntimeException;
import hello.dto.request.AddUserInputDTO;
import hello.dto.response.AddUserOutputDTO;
import hello.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author CHENJIANHUA001
 * @date 2019/03/18 15:28
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/hello/user")
public class UserController {

    /**
     * aa
     */
    @Autowired
    private UserService userService;

    @ApiOperation(value="添加用户", notes="添加用户")
    @PostMapping(path = "/add")
    public ResponseVO<AddUserOutputDTO> addNewUser(@RequestBody AddUserInputDTO param) {
        log.info("Handing request addNewUser begin, req: {}", JSONObject.toJSONString(param));

        AddUserOutputDTO addUserOutputDTO = new AddUserOutputDTO();
        addUserOutputDTO.setStatus("Saved");
        return ResponseVO.ok(addUserOutputDTO);
    }

    @GetMapping(path = "/exception")
    public ResponseVO exceptionTest(@RequestParam String name) {
        log.info("Handing request exceptionTest begin, req: {}");
        if(StringUtils.isBlank(name)){
            throw new MyRuntimeException(ResponseStatusEnum.PARAMETER_CHECK_ERROR);
        }
        JSONObject result = new JSONObject();
        result.put("userList",userService.getAllUsers());
        return ResponseVO.ok(result);
    }

    @GetMapping(path = "/all")
    public ResponseVO getAllUsers() {
        log.info("Handing request getAllUsers begin, req: {}");
        JSONObject result = new JSONObject();
        result.put("userList",userService.getAllUsers());
        return ResponseVO.ok(result);
    }
}
