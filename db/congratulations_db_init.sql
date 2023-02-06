
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS joke;
DROP TABLE IF EXISTS wish;

CREATE TABLE "user"
(
    id bigint PRIMARY KEY ,
    first_name varchar (30),
    language varchar (10)
);

CREATE TABLE joke
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title varchar(50),
    "text" text,
    language varchar (10)
);

CREATE TABLE wish
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "text" text,
    language varchar (10)
);


INSERT INTO joke (title, "text", language) VALUES
('Вовочка и окна',E'Вовочка пpовинился в школе. Диpектоp его к себе вызвал, дал кpаски ведpо и говоpит:\n— Вот, Вовочка, за это ты должен бyдешь покpасить все окна на тpетьем этаже. \nВовочка yшел. Возвpащается чеpез час. \n— Иван Петpович, а pамы кpасить.', 'ru'),
('Водительские права', E'— Скажите, пожалуйста, где можно получить права на вождение танка и бронетранспортёра?\n — Господа, катайтесь так! Кто вас остановит?!', 'ru'),
('Пьяный Вася', E'Мужчина пришел домой пьяный, весь избитый и начал прилеплять лейкопластырь на раны. Наутро встал, а жена его спрашивает: \n— Вася, ты вчера опять пьяный был? \n— Нет. \n— А кто же трезвый на зеркало лейкопластырь лепит?', 'ru'),
('Оптимизация',E'Ещё несколько лет такой же "оптимизации" медицины, и в торговых центрах будет не только проводится вакцинация, но и все виды амбулаторных хирургических операций.', 'ru'),
('Мудрая книга Талмуд', E'Еврей приходит к раввину: \n— Ребе, что мне делать? У меня восемь детей, я уже не могу прокормить такую ораву, а жена опять беременна и скоро родит. \nРебе открывает мудрую книгу Талмуд и на странице 235 читает: "Если у еврея жена все время рожает, то ему нужно отрезать одно яйцо". \nЧерез год опять приходит тот же еврей: \n— Ребе! Моя жена опять рожает! \nРебе открывает мудрую книгу Талмуд и на странице 236 читает: "Если еврею отрезали одно яйцо, а жена продолжает рожать, значит нужно отрезать еще одно яйцо". \nЧерез год к раввину снова приходит несчастный еврей: \n— Ребе, моя жена все равно рожает! \nРебе открывает мудрую книгу Талмуд и на странице 237 читает: "Если еврею отрезали оба яйца, а жена продолжает рожать, значит яйца отрезали не тому еврею".', 'ru'),
('Анекдот полковника', E'В кypилке полковник pассказывает анекдот. Все офицеpы, за исключением одного лейтенанта, смеются. \n— А вы, лейтенант, почемy не смеетесь? \n— А я не из вашей части, товаpищ полковник.', 'ru'),
('Маленькая проблема', E'Забил Лев косяк и решил дунуть под деревом в тенечке. Пару раз пыхнул и задремал, а как проснулся — косяка нет. Ну он зовет Орла.
— Ты там высоко летаешь, глянь кто моим косяком балует.
Орел полетел, вроде все нормально, там Заяц, Лиса, Волк все нормальные.
Вдруг видит, Ворона летит кверху ногами. Орел подлетает к ней:
— Слыш Ворона, ты косяк у Льва спиздила?
— Какой, нахуй косяк? Тут Землю спиздили!', 'ru'),
('Про юристов', E'Британские ученые решили проводить научные опыты вместо мышей на юристах.
Во-первых, юристов больше.
Во-вторых, мышей жалко.
И в-третьих, есть вещи, которые мыши отказываются делать', 'ru'),
('Чемпион', E'Чемпиона по сборке кубика Рубика случайно оставили одного за новогодним столом, и он за десять минут собрал из салата «Оливье» полбатона докторской колбасы, пять варёных картофелин, три морковки, пять яиц, четыре солёных огурца, полбанки зелёного горошка и пакетик майонеза', 'ru'),
('Штирлицу не спится', E'Штирлиц поднял трубку и услышал томный голос радистки Кэт:
— Вам, наверное, не спится без меня?
— Почему же? Спиться я могу и без вас, - ответил Штирлиц и налил очередной стакан водки.', 'ru'),
('Чистосердечное', E'Нашли ученые мумию - никак не могут понять кто такой.
Мучились, мучились, решили русский ученых вызвать.
Появились 2 мужчины в форме, потребовали освободить комнату...
Через полтора часа выходит один и, вытирая пот со лба, говорит:
" Это Аменхатеп 23-й, вот признание, вот подпись"', 'ru'),
('Та-щ Сталин', E'Мужик выходит на поле, видит небывалый урожай и в сердцах произносит:
-Ничего себе...
Сзади подходит товарищ Сталин и, гладя по голове, говорит:
-Правильно, всё нам.', 'ru'),
('Последние учения', E'СССР. Лётная часть. Учения у бомбардировщиков с полётами с настоящими боеприпасами. Посмотреть за взлётом бомбардировщиков на лётном поле около ВПП собрались несколько офицеров во главе с генералом. Бомбардировщики взлетают. Офицеры смотрят.
И вдруг при взлёте у бомбардировщика бомба срывается с подвеса, прыгает по ВПП.
Офицеры дружно прыгают в кювет лицом вниз, закрывают головы руками.
Генерал остаётся стоять где стоял. Задумчиво смотрит на извалявшихся в грязи подчинённых и изрекает:
-А чего вы прячетесь? Бомба то ядерная.', 'ru'),
('Про прапорщика', E'Отправили на работы за территорию в военной части 4х бойцов и прапора. После окончания работ построил прапор бойцов и считает :
- Раз, два, три, четыре. Нас было пятеро. Где пятый? Ищем пятого. Разойдись!
Проходит десять минут, опять построение и та же история. Та же команда:
- Разойдись, ищем пятого.
После третьего построения один боец говорит:
- Товарищ прапорщик, давайте я посчитаю, а вы становитесь в строй. Считает:
- Раз, два, три, четыре — и, показывая на себя - пять.
Прапор подходит к этому бойцу, отвешивает ему подзатыльник и говорит:
- Так это мы тебя, долбоёба, пол часа искали.', 'ru'),
('Говорящие часы', E'Мужик показывает квартиру своему другу.
Вдруг тот видит на стене большой медный таз.
— А это что еще такое?
— Говорящие часы!
— Это как?
Мужик со всей силой бьет по тазу.
Из-за стены раздается голос:
— Два часа ночи, еб твою мать!', 'ru'),
('Сектор приз на барабане', E'https://www.youtube.com/watch?v=gWTPOfXfCOI', 'ru'),
('Альпинисты', E'Альпинисты — они как яйца.\n
Либо крутые, либо всмятку', 'ru'),
('Парадокс', E'Я не верю в эволюцию. Если бы это было правдой,\n
Черные уже давно были бы пуленепробиваемыми.', 'ru'),
('Соблазн', E'Умерли грузин, хохол и еврей. И, попав на приём к богу, все втроём взмолились
— Отпусти нас обратно на землю, мало мы пожили ещё, молодые совсем.
— Хорошо,—ответил им бог,—но только до первого соблазна.
И вот очутились они на земле, в каком-то доме. Видят: стол накрыт, жратвы на нем видимо-невидимо. Только сел хохол за стол — тут же пропал.
А грузин и еврей вышли из дома и пошли дальше. Вдруг видят на дороге 100 долларов лежат. Еврей наклонился за деньгами и пропали оба.', 'ru'),
('Биология', E'Урок биологии в грузинской школе:
— Лэйла Вазгэновна, пачэму у человека в тэле тридцать мэтров кишечника?
— Это, Гоги, чтобэ горячим супом жопу не обжечь.', 'ru'),
('Курсач', E'— Писать курсач больнее, чем родить ребёнка.
— Ты когда-нибудь рожал ребенка?
— Нет, но женщины иногда говорят "Давай заведем ещё одного", но ни один студент не скажет "А напишу-ка я ещё один курсач"!', 'ru'),
('Freedom', 'An American and a Russian are arguing about which country has more freedom. The American says:
- I can walk right up to the White House and shout ''Down with Donald Trump!'' and nothing bad will happen to me.
The Russian replies:
- Guess what? I can walk in front of Kremlin and shout ''Down with Donald Trump!'' and nothing will happen to me either.', 'en'),
('Punishment', 'Question to Radio Armenia: «Is it possible to build Communism in a random capitalist country like, say, the Netherlands?» Answer: «Of course it’s possible but what have the Netherlands ever done to you?»', 'en'),
('The first and last time', 'Question: When was the first Russian election held? Answer: The time that God set Eve in front of Adam and said, "Go ahead, choose your wife."', 'en'),
('Bankruptcy reason', 'It seems that England''s royal family is running out of money. They are down to just $1.6 million. Well sure, that''s what happens when nobody in your family has had a job for the last thousand years.', 'en'),
('Useless thing', 'In fact, to make ends meet the Queen is thinking of having a yard sale. Getting rid of a lot of stuff they don''t use anymore, like Canada.', 'en'),
('Royal baby', 'Queen Elizabeth met the royal baby yesterday. The baby cried, so Queen Elizabeth explained: «You''ll never have to work a day in your life»', 'en'),
('Who is a liberal?', 'A liberal is just a conservative that hasn''t been mugged yet.', 'en'),
('Competition', 'Don’t steal, don’t lie and don’t cheat.The government hates competition.', 'en'),
('Joke', 'I don''t approve of political jokes...I''ve seen too many of them get elected.', 'en'),
('Love in theory', 'My love is like communism; everyone gets a share, and it''s only good in theory.', 'en'),
('Basement', 'Question: What do you call a basement full of Liberals? Answer: A whine cellar.', 'en'),
('Tomato transformation', 'Three tomatoes are walking down the street. Papa Tomato, Mama Tomato and Baby Tomato. Baby Tomato starts lagging behind, and Papa Tomato gets really angry. Goes back and squishes him and says: «Catch up»', 'en'),
('Couch potato', 'I work harder than God. If He had hired me, He would have made the world by Thursday.', 'en'),
('The main thing is motivation', 'A company owner was asked a question, "How do you motivate your employees to be so punctual?" He smiled and replied, "It''s simple. I have 30 employees and 29 free parking spaces. One is paid parking."', 'en'),
('Numeracy training', 'Tequila is an excellent teacher… Just last night it taught me to count… One Tequila, Two Tequila, Three Tequila, Floor!', 'en'),
('Idiot', 'I just saw some idiot at the gym… He put a water bottle in the Pringles holder on the treadmill!', 'en'),
('Electrician''s philosopher', 'My electrician''s favorite philosopher is Voltaire.', 'en'),
('No questions', 'A man pulls up to the curb and asks the policeman, "Can I park here?"
"No," says the cop.
"What about all these other cars?"
"They didn''t ask!"', 'en'),
('Reason', 'I signed up for an exercise class and was told to wear loose fitting clothing… If I had any loose fitting clothing, I wouldn''t have signed up in the first place!', 'en'),
('Salary', 'HR: "This is your revised salary. We recommend you keep it confidential."
Employee: "Don''t worry, I''m equally ashamed of it."', 'en');

INSERT INTO wish ("text", language) VALUES
('Пусть все твои мечты непременно сбудутся, всё, что загадано, обязательно исполнится! Улыбок, счастья, радости и веселья!', 'ru'),
('Желаю тебе удачи!', 'ru'),
('Желаю тебе долгих и счастливых лет!', 'ru'),
('Всего самого наилучшего!', 'ru'),
('Желаю тебе взаимопониманию с родными и близкми!', 'ru'),
('Крепкого здоровья тебе!', 'ru'),
('Желаю тебе всего хорошего: лучших друзей, внимательных близких, больше денег и свободы! Живи и радуйся, мечтай и воплощай, пой и смейся! Помни: это жизнь, и она прекрасна; бери от нее все!', 'ru'),
('Желаю тебе хорошего отдыха! Отдохни, оторвись как следует и наберись новых сил для достижения подвигов и больших и маленьких побед. Пусть все пройдет замечательно.', 'ru'),
('Праздничного настроения, счастья, удачи и всего самого доброго, светлого и прекрасного!', 'ru'),
('Пусть жизнь будет полна радости, счастливых событий и удовольствия!', 'ru'),
('Благополучие – величина непостоянная. Оно то есть, то его нет. Но когда оно есть, это всегда, как подарок судьбы. Желаю, чтобы судьба почаще баловала тебя такими подарками!', 'ru'),
('Жизнь наша состоит из самых разных дней. И чем больше дней спокойных, ярких, вдохновляющих, тем она приятнее. Пусть все у вас будет хорошо. Пусть каждый день будет удачным, пусть каждое новое дело ладится, не подводит здоровье, не оставляют удача и вдохновение. Пусть в любой жизненной ситуации у вас будет возможность стать победителем.', 'ru'),
('Понятие счастья у каждого своё. И я хочу от души пожелать тебе именно твоего счастья, именно того, что нужно тебе! Пусть оно ворвётся в жизнь быстрым вихрем, пусть затмит все невзгоды и мелкие неприятности. Пусть оно присутствует каждый день и наполняет сердце и душу светом и теплом. Счастья тебе много-много, всегда, везде и всюду!', 'ru'),
('Пусть удача всегда сопутствует тебе!', 'ru'),
('Желаю, чтобы этот день запомнился только приятными впечатлениями, яркими красками, добротой и отзывчивостью окружающих, интересными встречами и, конечно же, выполненными задачами.', 'ru'),
('Желаю тебе большого-большого счастья, любви, радости! Пусть все мечты сбываются, пусть здоровье будет крепким, пусть окружают доброжелательные, отзывчивые, прекрасные люди!', 'ru'),
('Пусть все твои мечты непременно сбудутся, всё, что загадано, обязательно исполнится! Улыбок, счастья, радости и веселья!', 'ru'),
('Желаю твоему настроению оставаться удивительно бодрым!', 'ru'),
('Пускай на душе царят благодать и покой!', 'ru'),
('Пусть хандра и тревоги обходят твой дом за версту!', 'ru'),
('В любой ситуации ищи лишь позитивные и добрые моменты!', 'ru'),
('Достигай желаемого!', 'ru'),
('May all your dreams come true for sure, everything you wish for will come true! Smiles, happiness, joy and fun!', 'en'),
('I wish you good luck!', 'en'),
('I wish you a long and happy life!', 'en'),
('All the best!', 'en'),
('I wish you understanding with your family and friends!', 'en'),
('Good health to you!', 'en'),
('I wish you all the best: best friends, attentive loved ones, more money and freedom! Live and rejoice, dream and fulfill, sing and laugh! Remember: this is life, and it''s beautiful; take from it everything!', 'en'),
('I wish you a good rest! Rest, have a great time and gain new strength to achieve feats and big and small victories. Let everything go great.', 'en'),
('Holiday mood, happiness, good luck and all the good, bright and beautiful!', 'en'),
('May life be full of joy, happy events, and pleasure!', 'en'),
('Well-being is a fickle thing. It is either there or it is not. But when it is there, it is always like a gift from fate. I wish fate spoils you with such gifts more often!', 'en'),
('Life is made up of all kinds of days. And the more days that are calm, bright, inspiring, the more pleasant it is. Let all be well with you. Let every day be successful, let every new business be successful, let your health not fail, let luck and inspiration not leave you. Let in any life situation you will have an opportunity to be a winner.', 'en'),
('Everyone has his own idea of happiness. And I want from the bottom of my heart to wish you exactly your happiness, exactly what you need! Let it burst into life with a quick whirlwind, let it overshadow all hardships and small troubles. Let it be present every day and fill your heart and soul with light and warmth. Happiness to you much, much, always, everywhere and everywhere!', 'en'),
('May fortune always be your companion!', 'en'),
('I wish that this day will be remembered only by pleasant impressions, bright colors, kindness and responsiveness of others, interesting meetings and, of course, accomplished tasks.', 'en'),
('I wish you great, great happiness, love and joy! Let all your dreams come true, let your health be strong, let you be surrounded by friendly, responsive, beautiful people!', 'en'),
('May all your dreams come true for sure, everything you wish for will come true! Smiles, happiness, joy and fun!', 'en'),
('Wishing your mood to remain wonderfully upbeat!', 'en'),
('May there be grace and peace in your soul!', 'en'),
('May moping and anxiety go around your house a mile away!', 'en'),
('In any situation, look only for positive and good moments!', 'en'),
('Get what you want!', 'en');