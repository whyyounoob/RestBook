package com.netcracker.borodin.controller;

import com.netcracker.borodin.details.UserDetailsImpl;
import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.dto.CommentFormDTO;
import com.netcracker.borodin.service.implement.CommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/comments")
public class CommentController {

  private final CommentServiceImpl commentServiceImpl;

  @Autowired
  public CommentController(CommentServiceImpl commentServiceImpl) {
    this.commentServiceImpl = commentServiceImpl;
  }

  @ApiOperation(value = "Gets all comments", nickname = "CommentController.getAllComments")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Comments")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Iterable<CommentDTO>> getAllComments() {
    Iterable<CommentDTO> comments = commentServiceImpl.findAllComments();
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets comments by current user",
      nickname = "CommentController.getCommentByUser")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Author")})
  @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CommentDTO>> getCommentsByUser(Authentication authentication) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<CommentDTO> comments = commentServiceImpl.showMyComment(userDetails.getId());
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets comments for specific book",
      nickname = "CommentController.getCommentsByBook")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Comments")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CommentDTO>> getCommentsByBook(@PathVariable("id") Long id) {
    List<CommentDTO> comments = commentServiceImpl.showCommentByBookId(id);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @ApiOperation(value = "Creates comment", nickname = "CommentController.createComment")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Author is created")})
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommentDTO> createComment(
      @RequestBody CommentFormDTO commentForm, Authentication authentication) {
    if (authentication != null) {

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      commentForm.setUserId(userDetails.getId());
      CommentDTO commentDTO = commentServiceImpl.addComment(commentForm);
      return new ResponseEntity<>(commentDTO, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "Update comment", nickname = "CommentController.updateComment")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Comment is updated")})
  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateComment(
      @RequestBody String text, Long commentId, Authentication authentication) {
    if (authentication != null) {

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      Long userID = userDetails.getId();
      if (commentServiceImpl.updateComment(commentId, text, userID)) {
        return new ResponseEntity<>("Comment updated", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Error in updating comment", HttpStatus.BAD_REQUEST);
      }

    } else {
      return new ResponseEntity<>("Authorize first", HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "Delete comment", nickname = "CommentController.deleteComment")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Comment is deleted")})
  @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteComment(
      @PathVariable("id") Long id, Authentication authentication) {
    if (authentication != null) {
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      Long userId = userDetails.getId();
      commentServiceImpl.deleteComment(id, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
