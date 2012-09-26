package ITfx.Messages;

public final class MessageBuilder {
    private final String id;
    private final String content;
    private final Message.Type type;
    private final Message.Category category;
    private final Message.Topic topic;

    public Message build() {
        return new Message(id,
                content,
                type != null ? type.toString() : Message.Type.Unknown.toString(),
                category != null ? category.toString() : Message.Category.None.toString(),
                topic != null ? topic.toString() : Message.Topic.None.toString());
    }

    public MessageBuilder id(String id) {
        return new MessageBuilder(id, content, type, category, topic);
    }

    public MessageBuilder content(String content) {
        return new MessageBuilder(id, content, type, category, topic);
    }

    public MessageBuilder type(Message.Type type) {
        return new MessageBuilder(id, content, type, category, topic);
    }

    public MessageBuilder category(Message.Category category) {
        return new MessageBuilder(id, content, type, category, topic);
    }

    public MessageBuilder topic(Message.Topic topic) {
        return new MessageBuilder(id, content, type, category, topic);
    }

    MessageBuilder(String id, String content, Message.Type type, Message.Category category, Message.Topic topic) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.category = category;
        this.topic = topic;
    }
}
