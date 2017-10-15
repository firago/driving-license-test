package com.dfirago.drivinglicensetest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfirago.drivinglicensetest.R;
import com.dfirago.drivinglicensetest.common.model.CategoryType;
import com.dfirago.drivinglicensetest.common.model.Question;
import com.dfirago.drivinglicensetest.common.utils.ViewGroupUtils;
import com.dfirago.drivinglicensetest.common.widget.AbstractQuestionView;
import com.dfirago.drivinglicensetest.common.widget.factories.QuestionViewFactory;
import com.dfirago.drivinglicensetest.fragments.listeners.TrainingOptionSelectionChangeListener;
import com.dfirago.drivinglicensetest.presenters.ExamPresenter;
import com.dfirago.drivinglicensetest.views.ExamView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by firag on 10/7/2017.
 */
public class ExamFragment extends BaseFragment implements ExamView {

    private static final String CATEGORY_PARAM = "categoryType";

    private CategoryType categoryType;
    private AbstractQuestionView currentQuestionView;

    @BindView(R.id.header_container)
    View headerContainer;
    @BindView(R.id.question_container)
    View questionContainer;
    @BindView(R.id.footer_container)
    View footerContainer;

    @Inject
    ExamPresenter examPresenter;
    @Inject
    QuestionViewFactory questionViewFactory;

    public static ExamFragment newInstance(CategoryType categoryType) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_PARAM, categoryType.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String categoryArg = getArguments().getString(CATEGORY_PARAM);
            categoryType = CategoryType.valueOf(categoryArg);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        examPresenter.loadQuestions(categoryType);
    }

    @Override
    public void showQuestion(Question question) {
        currentQuestionView = questionViewFactory
                .createView(question, questionContainer.getLayoutParams());
        currentQuestionView.setOptionSelectionChangeListener(
                new TrainingOptionSelectionChangeListener(currentQuestionView));
        ViewGroupUtils.replaceView(questionContainer, currentQuestionView);
        questionContainer = currentQuestionView;
    }
}
