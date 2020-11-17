package com.example.da.retrofit2;

import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.PhanHoi;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.entity.obj.comic.ComicChapter;
import com.example.da.entity.obj.comic.ComicQC;
import com.example.da.entity.obj.comic.ComicRead;
import com.example.da.entity.obj.comic.TheLoaiComic;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataService {

    //lấy gtri Json

    //ComicBook

    @GET("getComicQC.php")
    Call<List<ComicQC>> GetDataBannerComic();

    @GET("getComic.php")
    Call<List<ComicBook>> GetDataComicBook();

    @GET("getRankComic.php")
    Call<List<ComicBook>> GetDataRank();

    @GET("getTheLoaiComic.php")
    Call<List<TheLoaiComic>> GetDataTheLoaiComic();

    @FormUrlEncoded
    @POST("getChapterComic.php")
    Call<List<ComicChapter>> GetChapterComic(@Field("idComic") String idComic);

    @FormUrlEncoded
    @POST("getReadComic.php")
    Call<List<ComicRead>> GetReadComic(@Field("idChapter") String idChapter);

    @FormUrlEncoded
    @POST("getComic.php")
    Call<List<ComicBook>> GetComicGenre(@Field("genre") String genre);

    @FormUrlEncoded
    @POST("getComic.php")
    Call<List<ComicBook>> GetComicTheoQC(@Field("idQC") String idQC);

    @FormUrlEncoded
    @POST("getComicLike.php")
    Call<List<ComicBook>> GetComicLike(@Field("idAccount") String idAccount);

    @FormUrlEncoded
    @POST("checkComicLike.php")
    Call<String> CheckComicLike(@Field("idAccount") int idAccount, @Field("idComic") int idComic);

    @FormUrlEncoded
    @POST("insertComicLike.php")
    Call<String> InsertComicLike(@Field("idAccount") int idAccount, @Field("idComic") int idComic,@Field("isCheck") int isCheck);

    @FormUrlEncoded
    @POST("updateLikeComic.php")
    Call<String> UpdateLikeComic(@Field("likeComic") String likeComic, @Field("idComic") String idComic);

    @FormUrlEncoded
    @POST("updateViewComic.php")
    Call<String> UpdateViewComic(@Field("viewComic") String viewComic, @Field("idComic") String idComic);

    @FormUrlEncoded
    @POST("checkSLLike.php")
    Call<String> GetSLLike(@Field("idComic") String idComic);

    @FormUrlEncoded
    @POST("editAccountApp.php")
    Call<String> EditAccount(
            @Field("txtlastname") String lastName,
            @Field("txtname") String Name,
            @Field("txtphone") String Phone,
            @Field("txtaddress") String Address,
            @Field("txtemail") String Email,
            @Field("DateOfBirth") String DateofBirth,
            @Field("gender") int gender,
            @Field("idAccount") String idAccount);

    @FormUrlEncoded
    @POST("editPass.php")
    Call<String> EditPass(
            @Field("idAccount") String idAccount,
            @Field("oldPass") String oldPass,
            @Field("NewsPass") String NewsPass );

    //check read
    @FormUrlEncoded
    @POST("checkReading.php")
    Call<List<ComicChapter>> CheckReading(@Field("idAccount") String idAccount, @Field("idComic") String idComic);

    //update read
    @FormUrlEncoded
    @POST("updateReading.php")
    Call<String> UpdateReading(@Field("idAccount") String idAccount, @Field("idChapter") String idChapter);

    //dki accFB
    @FormUrlEncoded
    @POST("addFB.php")
    Call<String> AddFB(@Field("idAccount") String idAccount, @Field("name") String name, @Field("lastName") String lastName );

    //get acc FB
    @FormUrlEncoded
    @POST("getAccFB.php")
    Call<List<Account>> GetAccFB(@Field("username") String username);

    //update IMG
    @Multipart
    @POST("updateImgAcc.php")
    Call<String> UpdateImg(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insertImg.php")
    Call<String> AddImg(@Field("idAccount") String idAccount, @Field("img") String img, @Field("imgCu") String imgCu);

    //check Account
    @FormUrlEncoded
    @POST("checkAccount.php")
    Call<String> CheckTK(@Field("username") String username,
                         @Field("password") String password,
                         @Field("lastName") String lastname,
                         @Field("name") String name,
                         @Field("phone") String phone,
                         @Field("address") String address,
                         @Field("gender") String gender,
                         @Field("email") String email,
                         @Field("DateOfBirth") String DateOfBirth);

    //đnáh giá
    @FormUrlEncoded
    @POST("postCmtAndSao.php")
    Call<String> DanhGia(@Field("idAccount") String idAccount, @Field("Cmt") String Cmt, @Field("DanhGia") String DanhGia);
    @FormUrlEncoded
    @POST("postCmtAndSao.php")
    Call<String> DanhGiaSao(@Field("idAccount") String idAccount, @Field("DanhGia") String DanhGia);

    @FormUrlEncoded
    @POST("getCmtDD.php")
    Call<List<PhanHoi>> DanhGiaDD(@Field("idAccount") String idAccount);

    @FormUrlEncoded
    @POST("checkTB.php")
    Call<String> GetTBDoc(@Field("idAccount") String idAccount);

    @FormUrlEncoded
    @POST("checkTB.php")
    Call<String> GetTBChuaDoc(@Field("idAccount") String idAccount, @Field("idFeedback") String idFeedback);
}
