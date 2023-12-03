DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "points" CASCADE;

CREATE TABLE "users" (
                           "user_id" serial,
                           "login" varchar(50) NOT NULL,
                           "password" text NOT NULL,
                           CONSTRAINT "user_pkey" PRIMARY KEY ("user_id")
);

CREATE TABLE "points" (
                         "point_id" serial,
                         "x" double precision NOT NULL,
                         "y" double precision NOT NULL,
                         "r" double precision NOT NULL,
                         "user_id" bigint,
                         CONSTRAINT "point_pkey" PRIMARY KEY ("point_id"),
                         CONSTRAINT "fk_user" FOREIGN KEY(user_id)
                             REFERENCES users(user_id)
);