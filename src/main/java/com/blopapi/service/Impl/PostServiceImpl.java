package com.blopapi.service.Impl;

import com.blopapi.entity.Post;
import com.blopapi.exception.ResourceNotFoundException;
import com.blopapi.payload.PostDto;
import com.blopapi.repository.PostRepository;
import com.blopapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    private ModelMapper modelMapper;//this is not bulied in.//I went pickuped the dependency paste in downloaded library.
    //Because downloaded seprately library but object not created
    // @Autowired or constructor based dependency injection will not work directly when it is an external libray
    //postRepository was build in feature in spring boot.
    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }
    @Override    //this think
    public PostDto createPost(PostDto postDto) {

         Post post = mapToEntity(postDto);

        Post savedPost = postRepo.save(post);

         PostDto dto = mapToDto(savedPost);

        return dto;
    }

    // this think
    @Override
    public PostDto getPostById(long id) {
         Post post = postRepo.findById(id).orElseThrow(
                 ()->new ResourceNotFoundException(id)
         );

        PostDto dto = mapToDto(post);

        return dto;
    }

    //this think pagination concept
    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

             Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

         Page<Post> posts = postRepo.findAll(pageable);
         List<Post> content = posts.getContent();

        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return postDtos;
     }

    //delete record
    @Override
    public void deletPost(long id) {
         Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
         postRepo.deleteById(id);
    }

    //update record
    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
         Post updateContent = mapToEntity(postDto);
         updateContent.setId(post.getId());

         Post updatePostInfo = postRepo.save(updateContent);

         return mapToDto(updatePostInfo);

    }

    //this think today
    PostDto mapToDto(Post post){
       PostDto dto = modelMapper.map(post, PostDto.class);
       //dto.setId(post.getId());
        //dto.setTitle(post.getTitle());
        //dto.setDescription(post.getDescription());
        //dto.setContent(post.getContent());

        return dto;
    }

    //convert dto to entity//this think
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
       // Post post = new Post();
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());

        return post;
    }

}
