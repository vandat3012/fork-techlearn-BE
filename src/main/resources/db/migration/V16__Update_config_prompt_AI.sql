CREATE TABLE tbl_struct_response_ai
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    content_struct LONGTEXT              NULL,
    is_active      BIT(1)                NULL,
    type           LONGTEXT              NULL,
    CONSTRAINT pk_tbl_struct_response_ai PRIMARY KEY (id)
);

delete
from tbl_review_config;

insert into tbl_struct_response_ai (content_struct, is_active, type) value
    ('Nhận xét chi tiết từng class: chỉ hiển thị dòng nào bị sai', true, 'struct'), ('Đánh giá tổng quan: Nhận xét chung về tính đúng đắn, hiệu quả và tối ưu của chương trình.', true, 'struct'),
    ('Tiếng Việt', true, 'struct'),
    ('Tiếng Anh', false, 'language'),
    ('Tiếng Trung', false, 'language');
insert into tbl_review_config (prompt_structure, is_deleted, is_active)
    value ('Bây giờ tôi sẽ gửi cho bạn một chuỗi JSON. Json này trả về danh sách các class và nội dung của chúng. Tôi cần bạn thực hiện các yêu cầu sau (với mục đích review bài tập): Đề bài: {exercise} Yêu cầu: Nếu nội dung của bài làm không phù hợp với yêu cầu của đề bài, chỉ trả lời là ''Bạn đã nộp sai bài tập''. Nếu bài làm phù hợp với đề bài nhưng bị fail  thì thực hiện review với cấu trúc như sau: {struct} Kết quả: (chỉ trả lời Pass/Fail). Kết quả review phải trả về bằng {language} và theo chuẩn HTML (sử dụng các thẻ HTML từ <h3> trở lên để thể hiện rõ nội dung). Dưới đây là chuỗi JSON chứa nội dung class đã mã hóa Base64:', false, true)
