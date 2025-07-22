-- src/main/resources/db/data.sql

-- Member Table
INSERT INTO member_tb (id, name, email, password, role) VALUES (1, '관리자', 'admin@nate.com', '1234', 'ADMIN');
INSERT INTO member_tb (id, name, email, password, role) VALUES (2, '조충희', 'jch@nate.com', '1234', 'USER');
INSERT INTO member_tb (id, name, email, password, role) VALUES (3, '사용자', 'user@nate.com', '1234', 'USER');

-- Category Table
INSERT INTO category_tb (id, name) VALUES (1, '자유게시판');
INSERT INTO category_tb (id, name) VALUES (2, '질문과 답변');
INSERT INTO category_tb (id, name) VALUES (3, '공지사항');

-- Post Table (15개)
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (1, '첫 번째 글입니다', '내용1', 2, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (2, '두 번째 글입니다', '내용2', 2, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (3, '세 번째 글입니다', '내용3', 2, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (4, '네 번째 글입니다', '내용4', 3, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (5, '다섯 번째 글입니다', '내용5', 3, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (6, '여섯 번째 글입니다', '내용6', 2, 2, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (7, '일곱 번째 글입니다', '내용7', 2, 2, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (8, '여덟 번째 글입니다', '내용8', 3, 2, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (9, '아홉 번째 글입니다', '내용9', 3, 2, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (10, '열 번째 글입니다', '내용10', 2, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (11, '열한 번째 글입니다', '내용11', 2, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (12, '열두 번째 글입니다', '내용12', 3, 1, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (13, '공지사항입니다.', '중요한 내용입니다.', 1, 3, NOW(), 'NOTICE');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (14, 'JPA 질문 있습니다.', '이럴 땐 어떻게 해야 하나요?', 2, 2, NOW(), 'GENERAL');
INSERT INTO post_tb (id, title, content, member_id, category_id, created_at, post_type) VALUES (15, 'Mustache 사용법 질문', '반복문은 어떻게 쓰나요?', 3, 2, NOW(), 'GENERAL');

-- Reply Table
INSERT INTO reply_tb (id, comment, member_id, post_id, created_at) VALUES (1, '첫 번째 댓글입니다.', 3, 1, NOW());
INSERT INTO reply_tb (id, comment, member_id, post_id, created_at) VALUES (2, '두 번째 댓글입니다.', 2, 1, NOW());
INSERT INTO reply_tb (id, comment, member_id, post_id, created_at) VALUES (3, '정말 좋은 글이네요.', 3, 2, NOW());
INSERT INTO reply_tb (id, comment, member_id, post_id, created_at) VALUES (4, '답변입니다. 이렇게 해보세요.', 1, 14, NOW());
INSERT INTO reply_tb (id, comment, member_id, post_id, created_at) VALUES (5, '저도 궁금했는데 감사합니다.', 2, 14, NOW());
